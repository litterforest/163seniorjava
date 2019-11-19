package person.cobee.highperformanceprogramming.c1d2d3sync.cas.aba;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 放入到栈中的元素
 *
 * @author cobee
 * @since 2019-11-19
 */
@Getter
@Setter
@ToString
public class Node implements Serializable {
    private static final long serialVersionUID = -124514245856233752L;

    private String value;
    private String name;
    private Node next;

}
