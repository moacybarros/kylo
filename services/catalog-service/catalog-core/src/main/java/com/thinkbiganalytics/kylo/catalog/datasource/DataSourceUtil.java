package com.thinkbiganalytics.kylo.catalog.datasource;

/*-
 * #%L
 * kylo-catalog-core
 * %%
 * Copyright (C) 2017 - 2018 ThinkBig Analytics, a Teradata Company
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.thinkbiganalytics.kylo.catalog.dataset.DataSetUtil;
import com.thinkbiganalytics.kylo.catalog.rest.model.Connector;
import com.thinkbiganalytics.kylo.catalog.rest.model.DataSetTemplate;
import com.thinkbiganalytics.kylo.catalog.rest.model.DataSource;
import com.thinkbiganalytics.kylo.catalog.rest.model.DefaultDataSetTemplate;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

/**
 * Static utility methods for {@link DataSource} instances.
 */
public class DataSourceUtil {

    /**
     * Gets the paths for the specified data source.
     */
    @Nonnull
    public static Optional<List<String>> getPaths(@Nonnull final DataSource dataSource) {
        if (dataSource.getTemplate() != null && dataSource.getTemplate().getPaths() != null) {
            return Optional.of(dataSource.getTemplate().getPaths());
        } else {
            return Optional.of(dataSource).map(DataSource::getConnector).map(Connector::getTemplate).map(DataSetTemplate::getPaths);
        }
    }

    /**
     * Merges the data source and connector templates for the specified data source.
     */
    @Nonnull
    public static DefaultDataSetTemplate mergeTemplates(@Nonnull final DataSource dataSource) {
        final DefaultDataSetTemplate template = new DefaultDataSetTemplate();

        if (dataSource.getConnector() != null && dataSource.getConnector().getTemplate() != null) {
            DataSetUtil.mergeTemplates(template, dataSource.getConnector().getTemplate());
        }
        if (dataSource.getTemplate() != null) {
            DataSetUtil.mergeTemplates(template, dataSource.getTemplate());
        }

        return template;
    }

    /**
     * Instances of {@code DataSourceUtil} should not be constructed.
     */
    private DataSourceUtil() {
        throw new UnsupportedOperationException();
    }
}
