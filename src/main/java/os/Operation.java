package os;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Operation {
    enum Type { MATH, CONDITIONAL_BRANCH, UNCONDITIONAL_JUMP, IO_INSTRUCTION}
    private final Type type;
    private final String opCode;
    private final String sourceRegister1;
    private final String sourceRegister2;
    private final String destinationRegister;
    private final String branchRegister;
    private final String addressOrData; // ?
}