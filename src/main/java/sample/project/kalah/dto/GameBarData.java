package sample.project.kalah.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = GameBarData.GameBarDataBuilder.class)
public class GameBarData
{
    private List<Integer> playerBar;

    private Integer playerKalah;

    private List<Integer> oppositePlayerBar;
}
