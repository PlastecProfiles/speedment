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
package com.speedment.core.config;

import com.speedment.core.config.aspects.ParentHelper;
import com.speedment.api.config.ForeignKey;
import com.speedment.api.config.ForeignKeyColumn;
import com.speedment.api.config.Table;
import com.speedment.api.config.aspects.Parent;
import com.speedment.core.config.utils.ConfigUtil;
import com.speedment.util.Cast;
import groovy.lang.Closure;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class ForeignKeyImpl extends AbstractNamedConfigEntity implements ForeignKey, ParentHelper<ForeignKeyColumn> {

    private Table parent;
    private final ChildHolder children;
    
    public ForeignKeyImpl() {
        children = new ChildHolder();
    }
    
    @Override
    protected void setDefaults() {}

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.orFail(parent, Table.class);
    }

    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public ForeignKeyColumn addNewForeignKeyColumn() {
        final ForeignKeyColumn e = ForeignKeyColumn.newForeignKeyColumn();
        add(e);
        return e;
    }
    
    @Override
    public ForeignKeyColumn foreignKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewForeignKeyColumn);
    }
}