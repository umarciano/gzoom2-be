package it.mapsgroup.gzoom.mybatis;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Andrea Fossi.
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"it.mapsgroup.gzoom.mybatis.mapper"})
@ComponentScan({"it.mapsgroup.gzoom.mybatis.dao"})
public class MyBatisPersistenceConfiguration {
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
