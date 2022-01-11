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
//         val userDTO: Option[UserDTO] =
//            userDTOJson.flatMap(
//               Json.fromJson[UserDTO](_).asOpt
//            )

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
}
