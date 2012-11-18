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

package org.spymemcached.protocol.ascii;

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
import org.spymemcached.tapmessage.RequestMessage;
import org.spymemcached.tapmessage.TapOpcode;



/**
 * Operation factory for the ascii protocol.
 */
public class AsciiOperationFactory extends BaseOperationFactory {

  public DeleteOperation delete(String key, OperationCallback cb) {
    return new DeleteOperationImpl(key, cb);
  }

  public FlushOperation flush(int delay, OperationCallback cb) {
    return new FlushOperationImpl(delay, cb);
  }

  public GetAndTouchOperation getAndTouch(String key, int expiration,
      GetAndTouchOperation.Callback cb) {
    throw new UnsupportedOperationException("Get and touch is not supported "
        + "for ASCII protocol");
  }

  public GetOperation get(String key, GetOperation.Callback cb) {
    return new GetOperationImpl(key, cb);
  }

  public GetOperation get(Collection<String> keys, GetOperation.Callback cb) {
    return new GetOperationImpl(keys, cb);
  }

  public GetlOperation getl(String key, int exp, GetlOperation.Callback cb) {
    return new GetlOperationImpl(key, exp, cb);
  }

  public UnlockOperation unlock(String key, long casId,
          OperationCallback cb) {
    return new UnlockOperationImpl(key, casId, cb);
  }

  public GetsOperation gets(String key, GetsOperation.Callback cb) {
    return new GetsOperationImpl(key, cb);
  }

  public MutatorOperation mutate(Mutator m, String key, long by, long exp,
      int def, OperationCallback cb) {
    return new MutatorOperationImpl(m, key, by, cb);
  }

  public StatsOperation stats(String arg, StatsOperation.Callback cb) {
    return new StatsOperationImpl(arg, cb);
  }

  public StoreOperation store(StoreType storeType, String key, int flags,
      int exp, byte[] data, StoreOperation.Callback cb) {
    return new StoreOperationImpl(storeType, key, flags, exp, data, cb);
  }

  public KeyedOperation touch(String key, int expiration,
      OperationCallback cb) {
    throw new UnsupportedOperationException("Touch is not supported for "
        + "ASCII protocol");
  }

  public VersionOperation version(OperationCallback cb) {
    return new VersionOperationImpl(cb);
  }

  public NoopOperation noop(OperationCallback cb) {
    return new VersionOperationImpl(cb);
  }

  public CASOperation cas(StoreType type, String key, long casId, int flags,
      int exp, byte[] data, StoreOperation.Callback cb) {
    return new CASOperationImpl(key, casId, flags, exp, data, cb);
  }

  public ConcatenationOperation cat(ConcatenationType catType, long casId,
      String key, byte[] data, OperationCallback cb) {
    return new ConcatenationOperationImpl(catType, key, data, cb);
  }

  @Override
  protected Collection<? extends Operation> cloneGet(KeyedOperation op) {
    Collection<Operation> rv = new ArrayList<Operation>();
    GetOperation.Callback callback =
        new MultiGetOperationCallback(op.getCallback(), op.getKeys().size());
    for (String k : op.getKeys()) {
      rv.add(get(k, callback));
    }
    return rv;
  }

  public SASLMechsOperation saslMechs(OperationCallback cb) {
    throw new UnsupportedOperationException("SASL is not supported for "
        + "ASCII protocol");
  }

  public SASLStepOperation saslStep(String[] mech, byte[] challenge,
      String serverName, Map<String, ?> props, CallbackHandler cbh,
      OperationCallback cb) {
    throw new UnsupportedOperationException("SASL is not supported for "
        + "ASCII protocol");
  }

  public SASLAuthOperation saslAuth(String[] mech, String serverName,
      Map<String, ?> props, CallbackHandler cbh, OperationCallback cb) {
    throw new UnsupportedOperationException("SASL is not supported for "
        + "ASCII protocol");
  }

  @Override
  public TapOperation tapBackfill(String id, long date, OperationCallback cb) {
    throw new UnsupportedOperationException("Tap is not supported for ASCII"
        + " protocol");
  }

  @Override
  public TapOperation tapCustom(String id, RequestMessage message,
      OperationCallback cb) {
    throw new UnsupportedOperationException("Tap is not supported for ASCII"
        + " protocol");
  }

  @Override
  public TapOperation tapAck(TapOpcode opcode, int opaque,
      OperationCallback cb) {
    throw new UnsupportedOperationException("Tap is not supported for ASCII"
        + " protocol");
  }

  @Override
  public TapOperation tapDump(String id, OperationCallback cb) {
    throw new UnsupportedOperationException("Tap is not supported for ASCII"
        + " protocol");
  }
}
