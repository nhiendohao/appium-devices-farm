package integrations.slack.jsonreport;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JsonReport {
    private int totalTestCases;
    private int totalPasses;
    private int totalFails;
    private int totalSkips;
}
