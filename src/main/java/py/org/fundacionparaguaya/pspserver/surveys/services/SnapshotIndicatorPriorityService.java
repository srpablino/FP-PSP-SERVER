package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.IndicatorsPriority;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicatorPriority;

/**
 * 
 * @author mgonzalez
 *
 */
public interface SnapshotIndicatorPriorityService {
   
    IndicatorsPriority getSnapshotIndicatorPriorityList(Long snapshotIndicatorId);
    
    IndicatorsPriority addSnapshotIndicadorPriorityList(IndicatorsPriority priorities);
    
    IndicatorsPriority updateSnapshotIndicatorPriorityList(IndicatorsPriority priorities);
    
    void deleteSnapshotIndicatorPriority(Long snapshotIndicatorPriorityId);
    
    SnapshotIndicatorPriority updateSnapshotIndicatorPriority(SnapshotIndicatorPriority priority);  
    
    SnapshotIndicatorPriority addSnapshotIndicatorPriority(SnapshotIndicatorPriority priority);  
}
