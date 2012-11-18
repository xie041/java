/**
 * Copyright (C) 2006-2009 Dustin Sallings
 * Copyright (C) 2009-2012 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */

package org.spymemcached.protocol.binary;

import java.util.UUID;

import org.spymemcached.ops.OperationCallback;
import org.spymemcached.ops.OperationState;
import org.spymemcached.ops.TapOperation;
import org.spymemcached.tapmessage.RequestMessage;
import org.spymemcached.tapmessage.TapMagic;
import org.spymemcached.tapmessage.TapOpcode;
import org.spymemcached.tapmessage.TapRequestFlag;


/**
 * Implementation of a tap backfill operation.
 */
public class TapBackfillOperationImpl extends TapOperationImpl implements
    TapOperation {
  private final String id;
  private final long date;

  TapBackfillOperationImpl(String id, long date, OperationCallback cb) {
    super(cb);
    this.id = id;
    this.date = date;
  }

  @Override
  public void initialize() {
    RequestMessage message = new RequestMessage();
    message.setMagic(TapMagic.PROTOCOL_BINARY_REQ);
    message.setOpcode(TapOpcode.REQUEST);
    message.setFlags(TapRequestFlag.BACKFILL);
    message.setFlags(TapRequestFlag.SUPPORT_ACK);
    message.setFlags(TapRequestFlag.FIX_BYTEORDER);
    if (id != null) {
      message.setName(id);
    } else {
      message.setName(UUID.randomUUID().toString());
    }

    message.setBackfill(date);
    setBuffer(message.getBytes());
  }

  @Override
  public void streamClosed(OperationState state) {
    transitionState(state);
  }

  @Override
  public String toString() {
    return "Cmd: tap dump Flags: backfill,ack";
  }
}
