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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;

import org.spymemcached.ops.BaseOperationFactory;
import org.spymemcached.ops.CASOperation;
import org.spymemcached.ops.ConcatenationOperation;
import org.spymemcached.ops.ConcatenationType;
import org.spymemcached.ops.DeleteOperation;
import org.spymemcached.ops.FlushOperation;
import org.spymemcached.ops.GetAndTouchOperation;
import org.spymemcached.ops.GetOperation;
import org.spymemcached.ops.GetlOperation;
import org.spymemcached.ops.GetsOperation;
import org.spymemcached.ops.KeyedOperation;
import org.spymemcached.ops.MultiGetOperationCallback;
import org.spymemcached.ops.MultiGetsOperationCallback;
import org.spymemcached.ops.Mutator;
import org.spymemcached.ops.MutatorOperation;
import org.spymemcached.ops.NoopOperation;
import org.spymemcached.ops.Operation;
import org.spymemcached.ops.OperationCallback;
import org.spymemcached.ops.SASLAuthOperation;
import org.spymemcached.ops.SASLMechsOperation;
import org.spymemcached.ops.SASLStepOperation;
import org.spymemcached.ops.StatsOperation;
import org.spymemcached.ops.StoreOperation;
import org.spymemcached.ops.StoreType;
import org.spymemcached.ops.TapOperation;
import org.spymemcached.ops.UnlockOperation;
import org.spymemcached.ops.VersionOperation;
import org.spymemcached.ops.GetOperation.Callback;
import org.spymemcached.tapmessage.RequestMessage;
import org.spymemcached.tapmessage.TapOpcode;


/**
 * Factory for binary operations.
 */
public class BinaryOperationFactory extends BaseOperationFactory {

  public DeleteOperation
  delete(String key, OperationCallback operationCallback) {
    return new DeleteOperationImpl(key, operationCallback);
  }

  public UnlockOperation unlock(String key, long casId,
          OperationCallback cb) {
    return new UnlockOperationImpl(key, casId, cb);
  }
  public FlushOperation flush(int delay, OperationCallback cb) {
    return new FlushOperationImpl(cb);
  }

  public GetAndTouchOperation getAndTouch(String key, int expiration,
      GetAndTouchOperation.Callback cb) {
    return new GetAndTouchOperationImpl(key, expiration, cb);
  }

  public GetOperation get(String key, Callback callback) {
    return new GetOperationImpl(key, callback);
  }

  public GetOperation get(Collection<String> value, Callback cb) {
    return new MultiGetOperationImpl(value, cb);
  }

  public GetlOperation getl(String key, int exp, GetlOperation.Callback cb) {
    return new GetlOperationImpl(key, exp, cb);
  }

  public GetsOperation gets(String key, GetsOperation.Callback cb) {
    return new GetsOperationImpl(key, cb);
  }

  public MutatorOperation mutate(Mutator m, String key, long by, long def,
      int exp, OperationCallback cb) {
    return new MutatorOperationImpl(m, key, by, def, exp, cb);
  }

  public StatsOperation stats(String arg,
      org.spymemcached.ops.StatsOperation.Callback cb) {
    return new StatsOperationImpl(arg, cb);
  }

  public StoreOperation store(StoreType storeType, String key, int flags,
      int exp, byte[] data, StoreOperation.Callback cb) {
    return new StoreOperationImpl(storeType, key, flags, exp, data, 0, cb);
  }

  public KeyedOperation touch(String key, int expiration,
      OperationCallback cb) {
    return new TouchOperationImpl(key, expiration, cb);
  }

  public VersionOperation version(OperationCallback cb) {
    return new VersionOperationImpl(cb);
  }

  public NoopOperation noop(OperationCallback cb) {
    return new NoopOperationImpl(cb);
  }

  public CASOperation cas(StoreType type, String key, long casId, int flags,
      int exp, byte[] data, StoreOperation.Callback cb) {
    return new StoreOperationImpl(type, key, flags, exp, data, casId, cb);
  }

  public ConcatenationOperation cat(ConcatenationType catType, long casId,
      String key, byte[] data, OperationCallback cb) {
    return new ConcatenationOperationImpl(catType, key, data, casId, cb);
  }

  @Override
  protected Collection<? extends Operation> cloneGet(KeyedOperation op) {
    Collection<Operation> rv = new ArrayList<Operation>();
    GetOperation.Callback getCb = null;
    GetsOperation.Callback getsCb = null;
    if (op.getCallback() instanceof GetOperation.Callback) {
      getCb =
          new MultiGetOperationCallback(op.getCallback(), op.getKeys().size());
    } else {
      getsCb =
          new MultiGetsOperationCallback(op.getCallback(), op.getKeys().size());
    }
    for (String k : op.getKeys()) {
      rv.add(getCb == null ? gets(k, getsCb) : get(k, getCb));
    }
    return rv;
  }

  public SASLAuthOperation saslAuth(String[] mech, String serverName,
      Map<String, ?> props, CallbackHandler cbh, OperationCallback cb) {
    return new SASLAuthOperationImpl(mech, serverName, props, cbh, cb);
  }

  public SASLMechsOperation saslMechs(OperationCallback cb) {
    return new SASLMechsOperationImpl(cb);
  }

  public SASLStepOperation saslStep(String[] mech, byte[] challenge,
      String serverName, Map<String, ?> props, CallbackHandler cbh,
      OperationCallback cb) {
    return new SASLStepOperationImpl(mech, challenge, serverName, props, cbh,
        cb);
  }

  public TapOperation tapBackfill(String id, long date, OperationCallback cb) {
    return new TapBackfillOperationImpl(id, date, cb);
  }

  public TapOperation tapCustom(String id, RequestMessage message,
      OperationCallback cb) {
    return new TapCustomOperationImpl(id, message, cb);
  }

  public TapOperation
  tapAck(TapOpcode opcode, int opaque, OperationCallback cb) {
    return new TapAckOperationImpl(opcode, opaque, cb);
  }

  public TapOperation tapDump(String id, OperationCallback cb) {
    return new TapDumpOperationImpl(id, cb);
  }
}
