package person.cobee.distributesys.RPCServiceGovernanceFramework.n211rpctech.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import person.cobee.distributesys.RPCServiceGovernanceFramework.n211rpctech.DemoService;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-06-02
 */
public class DemoServiceStarter {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo/consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        System.out.println(demoService.sayHello("cobee"));
        System.out.println(demoService);
        context.close();
    }

}
