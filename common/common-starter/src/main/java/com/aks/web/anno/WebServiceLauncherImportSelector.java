package com.aks.web.anno;

import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * @author xxl
 * @since 2024/11/27
 */
public class WebServiceLauncherImportSelector implements ImportSelector {

    @Override
    @Nonnull
    public String[] selectImports(@Nonnull AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(WebServiceLauncher.class.getName()));
        if (Objects.nonNull(attributes) && attributes.getBoolean("hasDatasource")) {
            return new String[]{DataSourceAutoConfiguration.class.getName()};
        }
        return new String[]{};
    }
}
