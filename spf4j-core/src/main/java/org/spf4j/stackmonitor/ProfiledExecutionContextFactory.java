/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.stackmonitor;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.spf4j.base.ExecutionContext;
import org.spf4j.base.ExecutionContextFactory;
import org.spf4j.base.ThreadLocalScope;

/**
 * @author Zoltan Farkas
 */
@ParametersAreNonnullByDefault
public final class ProfiledExecutionContextFactory implements ExecutionContextFactory<ExecutionContext> {

  private final ConcurrentNavigableMap<Thread, ExecutionContext> currentContexts;

  private final ExecutionContextFactory<ExecutionContext> wrapped;

  public ProfiledExecutionContextFactory(final ExecutionContextFactory<ExecutionContext> wrapped) {
    this.wrapped = wrapped;
    this.currentContexts = new ConcurrentSkipListMap<>(ProfiledExecutionContextFactory::compare);
  }

  private static int compare(final Thread o1, final Thread o2) {
    return Long.compare(o1.getId(), o2.getId());
  }

  public Iterable<Thread> getCurrentThreads() {
    return currentContexts.keySet();
  }

  public Iterable<Map.Entry<Thread, ExecutionContext>> getCurrentThreadContexts() {
    return currentContexts.entrySet();
  }

  public ExecutionContext start(final String name, @Nullable final ExecutionContext parent,
          @Nullable final ExecutionContext previous,
          final  long startTimeNanos, final long deadlineNanos, final ThreadLocalScope onClose) {
    if (previous == null) {
      ExecutionContext ctx = wrapped.start(name, parent, null,
              startTimeNanos, deadlineNanos, new ThreadLocalScope() {
        @Override
        public void set(final ExecutionContext ctx) {
          if (ctx == null) {
            currentContexts.remove(Thread.currentThread());
          }
          onClose.set(ctx);
        }

        @Override
        public ExecutionContext getAndSet(final ExecutionContext ctx) {
          if (ctx == null) {
            currentContexts.remove(Thread.currentThread());
          }
          return onClose.getAndSet(ctx);
        }
      });
      currentContexts.put(Thread.currentThread(), ctx);
      return ctx;
    } else {
      return wrapped.start(name, parent, previous, startTimeNanos, deadlineNanos, onClose);
    }

  }

  @Override
  public String toString() {
    return "ProfiledExecutionContextFactory{" + "currentContexts="
            + currentContexts + ", wrapped=" + wrapped + '}';
  }

}