package us.wh.zlayerdemo
package infrastructure.todorepository

case class TodoSchema(
  id: String,
  userId: String,
  description: String,
  completed: Boolean
)
