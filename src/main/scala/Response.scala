package us.wh.zlayerdemo

import us.wh.zlayerdemo.domain.model.{Todo, User}

enum Response extends Product with Serializable {
  case ErrorResponse(message: String)
  case TodoListResponse(user: User, todoList: List[Todo])
}
