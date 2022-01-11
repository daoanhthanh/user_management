package models.services

import config.DbPool
import models.DTOs.UserDTO
import models.entities.User

import java.sql.{ResultSet, Statement}
import scala.collection.mutable.ListBuffer
import scala.util.{Try, Using}
import java.sql.SQLException
import scala.util.Success
import scala.util.Failure

class UserService {}

object UserService {

  /** @param dto
    *   an user DTO
    * @return
    *   an User object which has just been persist, include its auto-generated
    *   ID.
    */
  def persistOne(dto: UserDTO): Try[User] = {
    val INSERT_ONE_STATEMENT = "INSERT INTO users (name, dob) VALUES (?, ?);"
    Using(DbPool.getConnection) { conn =>
      {
        val statement = conn.prepareStatement(
          INSERT_ONE_STATEMENT,
          Statement.RETURN_GENERATED_KEYS
        )
        statement.setString(1, dto.name)
        statement.setDate(2, java.sql.Date.valueOf(dto.dob))
        val affectedRows = statement.executeUpdate()
        if (affectedRows > 0) {

          val user = Try[User] {
            val insertedResult: ResultSet = statement.getGeneratedKeys

            if (insertedResult.next) {

              User(
                insertedResult.getInt(1),
                dto.name,
                dto.dob
              )
            } else {
              throw new SQLException(s"Cannot generate key from with user $dto")
            }
          }
          user.get
        } else {
          throw new SQLException(s"Cannot persist new user $dto")
        }
      }
    }
  }

  // Get all User
  def getAll: List[User] = {
    val GET_ALL_STATEMENT = "SELECT * FROM users;"

    val users: Try[List[User]] = Using(DbPool.getConnection) { conn =>
      {
        val statement = conn.createStatement()
        val resultSet = statement.executeQuery(GET_ALL_STATEMENT)
        val response = new ListBuffer[User]
        while (resultSet.next()) {
          val id = resultSet.getInt(1)
          val name = resultSet.getString(2)
          val dob = resultSet.getDate(3).toLocalDate
          response addOne User(id, name, dob)
        }
        response.toList
      }
    }

    users.getOrElse(List.empty)
  }

  def getOne(userId: Int): Option[User] = {
    val GET_ONE_STATEMENT = "SELECT *  FROM users u WHERE u.id = ?;"

    val user: Try[User] = Using(DbPool.getConnection) { conn =>
      {
        var user = null.asInstanceOf[User]
        val prepareStatement = conn.prepareStatement(GET_ONE_STATEMENT)
        prepareStatement.setInt(1, userId)
        val resultSet = prepareStatement.executeQuery()
        while (resultSet.next()) {
          val id = resultSet.getInt(1)
          val name = resultSet.getString(2)
          val dob = resultSet.getDate(3).toLocalDate()
          user = User(id, name, dob)
        }
        user

      }
    }

    user match {
      case Success(_user) => Some(_user)
      case Failure(exception) => {
        exception.printStackTrace()
        None
      }
    }
  }

  // def updateOne(userId:Int, dto:UserDTO): Option[User] ={
  // 	val UPDATE_ONE = "UPDATE users SET name=? dob=? WHERE id=?;"
  //   val user: Try[User] = Using(DbPool.getConnection) { conn =>
  //     {
  //       var user = null.asInstanceOf[User]
  //       val prepareStatement = conn.prepareStatement(UPDATE_ONE)
  //       prepareStatement.setInt(1, userId)
  //       val resultSet = prepareStatement.executeQuery()
  //       while (resultSet.next()) {
  //         val id = resultSet.getInt(1)
  //         val name = resultSet.getString(2)
  //         val dob = resultSet.getDate(3).toLocalDate()
  //         user = User(id, name, dob)
  //       }
  //       user

  //     }
  //   }	}
}
