/*
 * Copyright (c) 2001, Zoltan Farkas All Rights Reserved.
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
 * IMPORTANT: This file are also Licensed with the BDS license.
 */
package org.spf4j.base;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import javax.annotation.Nullable;

/**
 * some "standard" process exit codes from:
 * https://www.freebsd.org/cgi/man.cgi?query=sysexits&apropos=0&sektion=0&manpath=FreeBSD+4.3-RELEASE&format=html
 *
 * @author zoly
 */
public enum SysExits {

  /**
   * The command was used incorrectly, e.g., with the wrong number of arguments, a bad flag, a bad syntax in a
   * parameter, or whatever.
   */
  EX_USAGE(64),
  /**
   * The input data was incorrect in some way. This should only be used for user's data and not system files.
   */
  EX_DATAERR(65),
  /**
   * An input file (not a system file) did not exist or was not readable. This could also include errors like ``No
   * message'' to a mailer (if it cared to catch it).
   */
  EX_NOINPUT(66),
  /**
   * The user specified did not exist. This might be used for mail addresses or remote logins.
   */
  EX_NOUSER(67),
  /**
   * The host specified did not exist. This is used in mail addresses or network requests.
   */
  EX_NOHOST(68),
  /**
   * A service is unavailable. This can occur if a support program or file does not exist. This can also be used as a
   * catchall message when something you wanted to do doesn't work, but you don't know why.
   */
  EX_UNAVAILABLE(69),
  /**
   * An internal software error has been detected. This should be limited to non-operating system related errors as
   * possible.
   */
  EX_SOFTWARE(70),
  /**
   * An operating system error has been detected. This is intended to be used for such things as ``cannot fork'',
   * ``cannot create pipe'', or the like. It includes things like getuid returning a user that does not exist in the
   * passwd file.
   */
  EX_OSERR(71),
  /**
   * Some system file (e.g., /etc/passwd, /var/run/utmp,etc.) does not exist, cannot be opened, or has some sort of
   * error (e.g., syntax error).
   */
  EX_OSFILE(72),
  /**
   * A (user specified) output file cannot be created.
   */
  EX_CANTCREAT(73),
  /**
   * An error occurred while doing I/O on some file.
   */
  EX_IOERR(74),
  /**
   * Temporary failure, indicating something that is not really an error. In sendmail, this means that a mailer (e.g.)
   * could not create a connection, and the request should be reattempted later.
   */
  EX_TEMPFAIL(75),
  /**
   * The remote system returned something that was ``not possible'' during a protocol exchange.
   */
  EX_PROTOCOL(76),
  /**
   * You did not have sufficient permission to perform the operation. This is not intended for file system problems,
   * which should use EX_NOINPUT or EX_CANTCREAT, but rather for higher level permissions.
   */
  EX_NOPERM(77),
  /**
   * Something was found in an unconfigured or misconfigured state.
   */
  EX_CONFIG(78);

  SysExits(final int code) {
    this.exitCode = code;
  }

  final int exitCode;

  public int exitCode() {
    return exitCode;
  }

  private static final TIntObjectMap<SysExits> CODE2ENUM;

  static {
    SysExits[] values = SysExits.values();
    TIntObjectMap<SysExits> c2e = new TIntObjectHashMap<>(values.length);
    for (SysExits e : values) {
      c2e.put(e.exitCode(), e);
    }
    CODE2ENUM = c2e;
  }

  @Nullable
  public static SysExits fromCode(final int exitCode) {
    return CODE2ENUM.get(exitCode);
  }

}