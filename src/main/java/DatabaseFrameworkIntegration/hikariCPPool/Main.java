package DatabaseFrameworkIntegration.hikariCPPool;

import DatabaseFrameworkIntegration.hikariCPPool.config.MainConfiguration;
import DatabaseFrameworkIntegration.hikariCPPool.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用HikariCP连接池
 * 前面我们提到了数据源还有其他实现 比如C3P0 Druid等 它们都是非常优秀的数据源实现(可以自行了解)
 * 不过我们这里介绍的 是之后在SpringBoot中还会遇到的HikariCP连接池
 *
 *      HikariCP是由日本程序员开源的一个数据库连接池组件 代码非常轻量 并且深度非常的快 根据官方提供的数据 在酷睿i7开启32个线程32个连接的情况下
 *      进行随机数据库读写操作 HikariCP的速度是现在常用的C3P0数据库连接池的数百倍 在SpringBoot3.0中 官方也是推荐使用HikariCP
 *
 *      https://smms.app/image/Q6gPI9RVe1X7Noq
 *
 * 首先 我们需要导入依赖:
 *
 *                  <dependency>
 *                      <groupId>com.zaxxer</groupId>
 *                      <artifactId>HikariCP</artifactId>
 *                      <version>5.0.1</version>
 *                  </dependency>
 *
 * 要更换数据源实现 非常简单 我们可以直接声明一个Bean:
 *
 *                  @Bean
 *                  public DataSource dataSource() {
 *
 *                      HikariDataSource dataSource = new HikariDataSource();
 *                      dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/study");
 *                      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
 *                      dataSource.setUsername("root");
 *                      dataSource.setPassword("123456");
 *                      return dataSource;
 *
 *                  }
 *
 * 最后我们发现 同样可以得到输出结果 但是出现了一个报错:
 *
 *                  SLF4J: No SLF4J providers were found.
 *                  SLF4J: Defaulting to no-operation (NOP) logger implementation
 *                  SLF4J: See http://www.slf4j.org/codes.html#noProviders for further details.
 *
 * 此数据源实际上是采用了SLF4J日志框架打印日志信息 但是现在没有任何的日志实现 (slf4j这是一个API标准 它规范了多种日志框架的操作
 * 统一使用SLF4J定义的方法来操作不同的日志框架 我们会在SpringBoot篇进行详细介绍) 我们这里就使用JUL作为日志实现 我们需要导入另一个依赖:
 *
 *                  <dependency>
 *                      <groupId>org.slf4j</groupId>
 *                      <artifactId>slf4j-jdk14</artifactId>
 *                      <version>1.7.25</version>
 *                  </dependency>
 *
 * 注意: 版本一定要和slf4j-api保持一致 我们可以在这里直接查看:
 *
 *      https://smms.app/image/93OSknRKXwdZsp7
 *
 *  这样 HikariCP数据源的启动日志就可以正常打印出来了:
 *
 *                  12月 4, 2023 3:41:38 下午 com.zaxxer.hikari.HikariDataSource getConnection
 *                  信息: HikariPool-1 - Starting...
 *                  2月 4, 2023 3:41:38 下午 com.zaxxer.hikari.pool.HikariPool checkFailFast
 *                  信息: HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@4f8969b0
 *                  12月 4, 2023 3:41:38 下午 com.zaxxer.hikari.HikariDataSource getConnection
 *                  信息: HikariPool-1 - Start completed.
 *                  Student(sid=1, name=小明, sex=男)
 *
 * 在SpringBoot阶段 我们还会遇到 HikariPool-1 —— Starting...和 HikariPool-1 —— Start completed同款日志信息
 *
 * 当然 Lombok肯定也是支持这个日志框架快速注解的:
 *
 *                  @slf4j
 *                  public class Main {
 *
 *                      public static void main() {
 *
 *                          ApplicationContext context = new AnnotationConfigApplicationContext();
 *                          TestMapper mapper = context.getBean(TestMapper.class);
 *                          log.info(mapper.getStudent().toString());
 *
 *                      }
 *
 *                  }
 */
@Slf4j
public class Main {

    static ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
    static TestMapper mapper = context.getBean(TestMapper.class);

    static void test1() {

        //System.out.println(mapper.getDept());
        log.info(mapper.getDept().toString());

    }

    public static void main(String[] args) {

        test1();

    }

}
