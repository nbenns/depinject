package com.caesars.ziotodo.infra.kvstore

import zio.stm.{TMap, ZSTM}

class KVStore[Key, Value](mem: TMap[Key, Value]) {
  def get(key: Key): ZSTM[Any, Nothing, Option[Value]] =
    mem.get(key)

  def getWith(f: Value => Boolean): ZSTM[Any, Nothing, List[Value]] =
    mem.fold(List.empty[Value]){ (acc, tuple) =>
      if (f(tuple._2)) tuple._2 :: acc
      else acc
    }.map(_.reverse)

  def set(key: Key, value: Value): ZSTM[Any, Nothing, Unit] =
    mem.put(key, value)

  def delete(key: Key): ZSTM[Any, Nothing, Unit] =
    mem.delete(key)
}
