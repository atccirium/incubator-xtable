/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.apache.xtable.conversion;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import com.google.common.base.Preconditions;

import org.apache.xtable.model.sync.SyncMode;

@Value
public class TableSyncConfig {
  // The source of the sync
  @NonNull SourceTable sourceTable;
  // One or more targets to sync the table metadata to
  List<TargetTable> targetTables;
  // The mode, incremental or snapshot
  SyncMode syncMode;
  // Additional properties to be used when initializing the conversion source
  Map<String, String> properties;

  @Builder
  TableSyncConfig(
      @NonNull SourceTable sourceTable,
      List<TargetTable> targetTables,
      SyncMode syncMode,
      Map<String, String> properties) {
    this.sourceTable = sourceTable;
    this.targetTables = targetTables;
    Preconditions.checkArgument(
        targetTables != null && !targetTables.isEmpty(),
        "Please provide at-least one format to sync");
    this.syncMode = syncMode == null ? SyncMode.INCREMENTAL : syncMode;
    this.properties = properties == null ? Collections.emptyMap() : properties;
  }
}
