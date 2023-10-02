package sample.project.kalah.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("gameConfigurationService")
public class GameConfigurationService
{
    @Value("${server.scheme}")
    private String scheme;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Value("${server.startWebPageValue}")
    private String startWebPageValue;

    @Value("${server.playWebPageValue}")
    private String playWebPageValue;

    @Value("${server.errorWebPageValue}")
    private String errorWebPageValue;

    @Value("${kalah.bar.numberOfStonesInEachHole}")
    private Integer numberOfStonesInEachHole;

    @Value("${kalah.bar.numberOfHolesForEachParticipant}")
    private Integer numberOfHolesForEachParticipant;

    public Integer getNumberOfStonesInEachHole()
    {
        return numberOfStonesInEachHole;
    }

    public Integer getNumberOfHolesForEachParticipant()
    {
        return numberOfHolesForEachParticipant;
    }

    public String getPlayWebPageValue()
    {
        return playWebPageValue;
    }

    public String getStartWebPageValue()
    {
        return startWebPageValue;
    }

    public String getErrorWebPageValue()
    {
        return errorWebPageValue;
    }

    public String getServerScheme()
    {
        return scheme;
    }

    public String getServerHost()
    {
        return host;
    }

    public String getServerPort()
    {
        return port;
    }
}
