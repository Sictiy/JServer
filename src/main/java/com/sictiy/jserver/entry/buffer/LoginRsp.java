// automatically generated by the FlatBuffers compiler, do not modify

package com.sictiy.jserver.entry.buffer;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class LoginRsp extends Table {
  public static LoginRsp getRootAsLoginRsp(ByteBuffer _bb) { return getRootAsLoginRsp(_bb, new LoginRsp()); }
  public static LoginRsp getRootAsLoginRsp(ByteBuffer _bb, LoginRsp obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; vtable_start = bb_pos - bb.getInt(bb_pos); vtable_size = bb.getShort(vtable_start); }
  public LoginRsp __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public boolean result() { int o = __offset(4); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }

  public static int createLoginRsp(FlatBufferBuilder builder,
      boolean result) {
    builder.startObject(1);
    LoginRsp.addResult(builder, result);
    return LoginRsp.endLoginRsp(builder);
  }

  public static void startLoginRsp(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addResult(FlatBufferBuilder builder, boolean result) { builder.addBoolean(0, result, false); }
  public static int endLoginRsp(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}

