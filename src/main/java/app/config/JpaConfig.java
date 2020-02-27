package app.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Configuration
public class JpaConfig {
    @Bean
    @ConditionalOnMissingBean
    public DataSource embeddedDataBase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource mysql() {
        ResourceBundle properties = ResourceBundle.getBundle("datasource");
        return DataSourceBuilder.create()
                .driverClassName(properties.getString("driver-class"))
                .url(properties.getString("url"))
                .username(properties.getString("username"))
                .password(properties.getString("password"))
                .build();
    }
}
