// automatically generated by the FlatBuffers compiler, do not modify

package com.sictiy.jserver.entry.buffer;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class LoginReq extends Table {
  public static LoginReq getRootAsLoginReq(ByteBuffer _bb) { return getRootAsLoginReq(_bb, new LoginReq()); }
  public static LoginReq getRootAsLoginReq(ByteBuffer _bb, LoginReq obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public LoginReq __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String userName() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer userNameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer userNameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String password() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer passwordAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer passwordInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createLoginReq(FlatBufferBuilder builder,
      int userNameOffset,
      int passwordOffset) {
    builder.startObject(2);
    LoginReq.addPassword(builder, passwordOffset);
    LoginReq.addUserName(builder, userNameOffset);
    return LoginReq.endLoginReq(builder);
  }

  public static void startLoginReq(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addUserName(FlatBufferBuilder builder, int userNameOffset) { builder.addOffset(0, userNameOffset, 0); }
  public static void addPassword(FlatBufferBuilder builder, int passwordOffset) { builder.addOffset(1, passwordOffset, 0); }
  public static int endLoginReq(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

