/**
 * Copyright (C) 2006-2009 Dustin Sallings
 * Copyright (C) 2009-2011 Couchbase, Inc.
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

import org.spymemcached.ops.DeleteOperation;
import org.spymemcached.ops.OperationCallback;

class DeleteOperationImpl extends SingleKeyOperationImpl implements
    DeleteOperation {

  private static final byte CMD = 0x04;

  private final long cas;

  public DeleteOperationImpl(String k, OperationCallback cb) {
    this(k, 0, cb);
  }

  public DeleteOperationImpl(String k, long c, OperationCallback cb) {
    super(CMD, generateOpaque(), k, cb);
    cas = c;
  }

  @Override
  public void initialize() {
    prepareBuffer(key, cas, EMPTY_BYTES);
  }

  @Override
  public String toString() {
    return super.toString() + " Cas: " + cas;
  }
}
