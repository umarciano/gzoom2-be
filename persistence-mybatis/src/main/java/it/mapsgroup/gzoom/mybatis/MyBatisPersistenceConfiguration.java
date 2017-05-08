package it.mapsgroup.gzoom.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"it.mapsgroup.gzoom.mybatis.mapper"})
@ComponentScan({ "it.mapsgroup.gzoom.mybatis.dao"})
public class MyBatisPersistenceConfiguration {


    @Bean(name = "mainDataSource")
    @Autowired
    public DataSource mainDataSource(Environment environment) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(getNotNullProperty(environment, "persistence.main.driver"));
        dataSource.setJdbcUrl(getNotNullProperty(environment, "persistence.main.url"));
        dataSource.setUsername(getNotNullProperty(environment, "persistence.main.user"));
        dataSource.setPassword(getNotNullProperty(environment, "persistence.main.password"));
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setMinimumIdle(10);
        dataSource.setMaximumPoolSize(50);
        return dataSource;
    }

    private String getNotNullProperty(Environment environment, String key) {
        String value = environment.getProperty(key);
        if (StringUtils.isNoneBlank(value))
            return value;
        else
            throw new RuntimeException(key + " is null");
    }

    @Autowired
    @Bean(name = "txManager")
    public DataSourceTransactionManager txManager(@Qualifier("mainDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    @Bean(name = "transactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("txManager") DataSourceTransactionManager txManager) {
        return new TransactionTemplate(txManager);
    }


    @Autowired
    @Bean(name = "mainSqlSessionFactory")
    public SqlSessionFactoryBean mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());

      //  sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("/it/memelabs/smartnebula/lmm/persistence/main/mapper/**/*.xml"));

        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("/it/mapsgroup/gzoom/mybatis/mapper/**/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/META-INF/mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }

}
