package org.gpc.template.kernel;

import java.util.UUID;

public record Favorite(UUID id,
                       UUID userId,
                       UUID proteinId,
                       String fastaName)
                       {

}




