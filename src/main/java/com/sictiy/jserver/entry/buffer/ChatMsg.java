// automatically generated by the FlatBuffers compiler, do not modify

package com.sictiy.jserver.entry.buffer;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ChatMsg extends Table {
  public static ChatMsg getRootAsChatMsg(ByteBuffer _bb) { return getRootAsChatMsg(_bb, new ChatMsg()); }
  public static ChatMsg getRootAsChatMsg(ByteBuffer _bb, ChatMsg obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public ChatMsg __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int type() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public long target() { int o = __offset(6); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public String msg() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer msgAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer msgInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }

  public static int createChatMsg(FlatBufferBuilder builder,
      int type,
      long target,
      int msgOffset) {
    builder.startObject(3);
    ChatMsg.addTarget(builder, target);
    ChatMsg.addMsg(builder, msgOffset);
    ChatMsg.addType(builder, type);
    return ChatMsg.endChatMsg(builder);
  }

  public static void startChatMsg(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addType(FlatBufferBuilder builder, int type) { builder.addInt(0, type, 0); }
  public static void addTarget(FlatBufferBuilder builder, long target) { builder.addLong(1, target, 0L); }
  public static void addMsg(FlatBufferBuilder builder, int msgOffset) { builder.addOffset(2, msgOffset, 0); }
  public static int endChatMsg(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
