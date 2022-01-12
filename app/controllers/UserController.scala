package controllers

import models.DTOs.UserDTO
import models.entities.User
import models.services.UserService
import play.api.libs.json.{Format, JsString, JsSuccess, Json, OFormat, OWrites}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import java.time.LocalDate
import javax.inject.Inject
import scala.util.{Failure, Success}

class UserController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

   implicit lazy val userDtoFormat: Format[UserDTO] = Json.format[UserDTO]
   implicit lazy val userFormat: Format[User] = Json.format[User]

   implicit lazy val localDateFormat: Format[LocalDate] = Format[LocalDate](
      value => {
         JsSuccess(LocalDate.parse(value.as[String]))
      },
      date => JsString(date.toString)
   )


   //CRUD
   def addOne(): Action[AnyContent] = Action {
      implicit request => {
         val userDTO = request.body.asJson.map(_.as[UserDTO])
         userDTO match {
            case Some(dto) => {
               val response = UserService.persistOne(dto)
               response match {
                  case Success(value) => Created(Json.toJson(value))
                  case Failure(e) => println(e)
                     NotAcceptable
               }
            }
            case _ => BadRequest
         }
      }
   }

   def getAll: Action[AnyContent] = Action {
      val response = UserService.getAll

      Ok(Json.toJson(response))
   }

   def getOne(userId:Int): Action[AnyContent] = Action{
      val response = UserService.getOne(userId)
      response match {
         case Some(value) => {
            if(value != null)Ok(Json.toJson(value))
            else NotFound
         }
         case None => NotFound
      }
   }

   
}
