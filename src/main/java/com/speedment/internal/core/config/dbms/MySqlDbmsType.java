/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.core.config.dbms;

import com.speedment.Speedment;
import com.speedment.config.Dbms;
import com.speedment.db.DbmsHandler;
import com.speedment.internal.core.db.MySqlDbmsHandler;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class MySqlDbmsType extends AbstractDbmsType {

    private static final BiFunction<Speedment, Dbms, DbmsHandler> DBMS_MAPPER = MySqlDbmsHandler::new; // JAVA8 bug: Cannot use method ref in this() or super()
    private static final String RESULTSET_TABLE_SCHEMA = "TABLE_SCHEMA";
    private static final String JDBC_CONNECTOR_NAME = "mysql";
    private static final Optional<String> DEFAULT_CONNECTOR_PARAMS = Optional.of("useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull");
    private static final Function<Dbms,String> CONNECTION_URL_GENERATOR = dbms -> {
        final StringBuilder result = new StringBuilder();
        result.append("jdbc:").append(JDBC_CONNECTOR_NAME).append("://");
        dbms.getIpAddress().ifPresent(ip -> result.append(ip));
        dbms.getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/");
        DEFAULT_CONNECTOR_PARAMS.ifPresent(d -> result.append("?").append(d));
        return result.toString();
    };

    public MySqlDbmsType() {

        super(
            "MySQL",
            "MySQL-AB JDBC Driver",
            3306,
            ".",
            "Just a name",
            "com.mysql.jdbc.Driver",
            DEFAULT_CONNECTOR_PARAMS.get(),
            JDBC_CONNECTOR_NAME,
            "`",
            "`",
            Stream.of("MySQL", "information_schema").collect(Collectors.collectingAndThen(toSet(), Collections::unmodifiableSet)),
            DBMS_MAPPER,
            RESULTSET_TABLE_SCHEMA,
            CONNECTION_URL_GENERATOR
        );
    }

}
