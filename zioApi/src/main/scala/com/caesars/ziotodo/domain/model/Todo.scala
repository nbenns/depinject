package com.caesars.ziotodo.domain.model

case class Todo(
  id: String,
  userId: String,
  description: String,
  completed: Boolean
)
