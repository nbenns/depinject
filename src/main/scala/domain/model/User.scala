package us.wh.zlayerdemo
package domain.model

import us.wh.zlayerdemo.domain.UserStore
import zio.*

case class User(
  id: String,
  firstName: String,
  lastName: String,
  banned: Boolean
)
