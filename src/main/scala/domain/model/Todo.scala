package us.wh.zlayerdemo
package domain.model

import us.wh.zlayerdemo.domain.TodoStore
import zio.*

case class Todo(
  id: String,
  userId: String,
  description: String,
  completed: Boolean
)
