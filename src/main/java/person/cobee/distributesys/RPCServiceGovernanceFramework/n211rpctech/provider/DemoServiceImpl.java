package person.cobee.distributesys.RPCServiceGovernanceFramework.n211rpctech.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import person.cobee.distributesys.RPCServiceGovernanceFramework.n211rpctech.DemoService;

import java.io.IOException;

/**
 * 功能描述
 *
 * @author cobee
 * @since 2020-06-02
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
