package models.entities

import java.time.LocalDate
import java.time.format.{DateTimeFormatter, FormatStyle}

case class User(id: Int, name: String, dob: LocalDate) {
  override def toString: String = s"[User: id=$id, name=$name, dob=${dob.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))}]"
}
