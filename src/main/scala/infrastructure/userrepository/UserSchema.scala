package us.wh.zlayerdemo
package infrastructure.userrepository

case class UserSchema(
  id: String,
  firstName: String,
  lastName: String,
  banned: Boolean
)
