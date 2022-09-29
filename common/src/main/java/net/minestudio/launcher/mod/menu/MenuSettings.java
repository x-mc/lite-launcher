package net.minestudio.launcher.mod.menu;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSettings {
  private MenuPolicy policy;
  private MenuPlayerState playerState;
}
