package src.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldClock 
{
    //private String $id;
	private String currentDateTime;
	private String utcOffset;
	private Boolean isDayLightSavingsTime;
	private String dayOfTheWeek;
	private String timeZoneName;
	private Long currentFileTime;
	private String ordinalDate;
	private String serviceResponse;
}
