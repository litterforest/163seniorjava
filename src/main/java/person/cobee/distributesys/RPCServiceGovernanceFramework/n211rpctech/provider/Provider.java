package person.cobee.distributesys.RPCServiceGovernanceFramework.n211rpctech.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-06-02
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo/provider.xml");
        context.start();
        System.in.read();
    }
}
