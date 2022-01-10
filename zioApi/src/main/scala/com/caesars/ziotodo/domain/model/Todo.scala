package com.caesars.ziotodo.domain.model

import com.caesars.ziotodo.adapters.output.TodoRepoKVStore
import zio.*

case class Todo(
  id: String,
  userId: String,
  description: String,
  completed: Boolean
)
