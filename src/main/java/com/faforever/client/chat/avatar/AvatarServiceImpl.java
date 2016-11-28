package com.faforever.client.chat.avatar;

import com.faforever.client.remote.AssetService;
import com.faforever.client.remote.FafService;
import javafx.scene.image.Image;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.faforever.client.config.CacheNames.AVATARS;
import static com.github.nocatch.NoCatch.noCatch;

@Lazy
@Service
public class AvatarServiceImpl implements AvatarService {

  @Inject
  FafService fafService;
  @Inject
  AssetService assetService;

  @Override
  @Cacheable(AVATARS)
  public Image loadAvatar(String avatarUrl) {
    return assetService.loadAndCacheImage(noCatch(() -> new URL(avatarUrl)), Paths.get("avatars"), null);
  }

  @Override
  public CompletionStage<List<AvatarBean>> getAvailableAvatars() {
    return fafService.getAvailableAvatars();
  }

  @Override
  public void changeAvatar(AvatarBean avatar) {
    fafService.selectAvatar(avatar);
  }
}
