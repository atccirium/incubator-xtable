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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import org.apache.hadoop.fs.Path;

import com.google.common.base.Preconditions;

@Getter
@EqualsAndHashCode
class ExternalTable {
  @NonNull String name;
  @NonNull String formatName;
  @NonNull String metadataPath;
  String[] namespace;
  CatalogConfig catalogConfig;

  ExternalTable(
      @NonNull String name,
      @NonNull String formatName,
      @NonNull String basePath,
      String[] namespace,
      CatalogConfig catalogConfig) {
    this.name = name;
    this.formatName = formatName;
    this.metadataPath = sanitizeBasePath(basePath);
    this.namespace = namespace;
    this.catalogConfig = catalogConfig;
  }

  protected String sanitizeBasePath(String tableBasePath) {
    Path path = new Path(tableBasePath);
    Preconditions.checkArgument(path.isAbsolute(), "Table base path must be absolute");
    if (path.isAbsoluteAndSchemeAuthorityNull()) {
      // assume this is local file system and append scheme
      return "file://" + path;
    } else if (path.toUri().getScheme().equals("file")) {
      // add extra slashes
      return "file://" + path.toUri().getPath();
    } else {
      return path.toString();
    }
  }
}
