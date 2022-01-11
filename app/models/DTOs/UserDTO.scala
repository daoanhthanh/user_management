package models.DTOs

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

case class UserDTO(name: String, dob: LocalDate) {
  override def toString: String =
    s"User: name=$name dob=${dob.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))}"
}
