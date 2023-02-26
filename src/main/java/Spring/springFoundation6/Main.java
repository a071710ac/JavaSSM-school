package Spring.springFoundation6;

import Spring.springFoundation6.entity.Student6;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 工厂模式和工厂Bean
 * 前面我们介绍了IoC容器的Bean创建机制 默认情况下 容器会调用Bean对应类型的构造方法进行对象创建 但是再某些时候
 * 我们可能不希望外界使用类的构造方法完成对象创建 比如在工厂方法设计模式中(详情请观看《Java设计模式》章节)
 * 我们更希望Spring不要直接利用反射机制通过构造方法创建Bean对象 而是利用反射机制先找到对应的工厂类 然后利用工厂类去生成需要的Bean对象
 *
 *                  public class Student {
 *                      Student() {
 *                          System.out.println("我被构造了");
 *                      }
 *                  }
 *
 *                  public class Student6Factory {
 *                      public static Student getStudent1() {
 *
 *                          System.out.println("欢迎光临电子厂");
 *                          return new Student();
 *
 *                      }
 *                  }
 *
 * 此时Student有一个工厂 我们正常情况下需要使用工厂才可以得到Student对象 现在我们希望Spring也这样做 不要直接去反射搞结构方法创建 我们可以通过factory-method进行指定:
 *
 *                  <bean name="factory" class="Spring.springFoundation6.entity.Student6Factory" factory-method="getStudent1"/>
 *
 * 注意 这里的Bean类型需要填写为Student类的工厂类 并且添加factory-method指定对应工厂方法 但是最后注册的是工厂方法的返回类型 所以依然是Student的Bean:
 *
 *      https://img-blog.csdnimg.cn/img_convert/d48b998ba560d3f979fc1f2527e9b488.png
 *
 * 此时我们再去进行获取 拿到的也是通过工厂方法得到的对象:
 *
 *      https://img-blog.csdnimg.cn/img_convert/51f55b1b6e478e8038db3a3977d9ca96.png
 *
 * 这里有一个误区 千万不要认为是我们注册了StudentFactory这个Bean class填写为这个类这个只是为了告诉Spring我们的工厂方法在哪个位置 真正注册的是工厂方法提供的东西
 *
 * 可以发现 当我们采用工厂模式后 我们就无法再通过配置文件对Bean进行依赖注入等操作了 而是只能在工厂方法中完成 这似乎与Spring的设计理念背道而驰?
 *
 * 当然 可能某些工厂类需要构造出对象之后才能使用 我们也可以将某个工厂类直接注册为工厂Bean:
 *
 *                  public class StudentFactory {
 *                      public Student getStudent2() {
 *
 *                          System.out.println("欢迎光临电子厂");
 *                          return new Student;
 *
 *                      }
 *                  }
 *
 * 现在需要StudentFactory对象才可以获取到Student 此时我们就只能先将其注册为Bean了:
 *
 *                  <bean name="factory" class="Spring.springFoundation6.entity.Student6Factory"/>
 *
 * 像这样将工厂注册为Bean 我们称其为工厂Bean 然后再使用factory-bean来指定Bean的工厂Bean:
 *
 *                  <bean factory-bean="factory" factory-method="getStudent2"/>
 *
 * 注意 使用factory-bean之后 不再要求指定class 我们可以直接使用了:
 *
 *      https://img-blog.csdnimg.cn/img_convert/0acb92955b4303a9148fc97e34378d18.png
 *
 * 此时可以看到 工厂方法上同样有了图标 这种方式 由于工厂类被注册为Bean 此时我们就可以再配置文件中为工厂Bean配置依赖注入等内容了
 *
 * 这里还有一个很细节的操作 如果我们想获取工厂Bean为我们提供的Bean 可以直接输入工厂Bean的名称 这样不会得到工厂Bean的实例 而是工厂Bean生产的Bean的实例:
 *
 *                  Student6 bean = (Student6) context.getBean("factory");
 *
 * 当然 如果我们需要获取工厂类的实例 可以在名称前面添加&符号:
 *
 *                  Student6 bean = (StudentFactory) context.getBean("&studentFactory);
 *
 * 又是一个小细节
 */
public class Main {

    static void test1() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationFoundation.xml");

        // System.out.println(context.getBean(Student6.class));
        System.out.println(context.getBean("factory"));

    }

    public static void main(String[] args) {

        test1();

    }

}
