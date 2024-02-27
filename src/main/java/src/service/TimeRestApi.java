package src.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import src.models.WorldClock;
@Service
public class TimeRestApi 
{
    /**
     * 
     * @return objeto (tipo WordClock) que contiene informacion sobre la fecha y la hora actual
     */
    public WorldClock getTime() 
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            final String url = "http://worldclockapi.com/api/json/utc/now";
            return restTemplate.getForObject(url, WorldClock.class);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
