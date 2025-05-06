package com.food.ordering.system.service.domain.dto.track;


import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author juliwolf
 */

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderQuery {
  @NonNull
  private final UUID orderTrackingId;
}
