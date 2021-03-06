package com.brandon3055.draconicevolution.client.render.tile;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.blocks.DraconiumChest;
import com.brandon3055.draconicevolution.blocks.tileentity.TileDraconiumChest;
import com.brandon3055.draconicevolution.client.DETextures;
import com.brandon3055.draconicevolution.client.model.ModelDraconiumChest;
import com.brandon3055.draconicevolution.init.DEContent;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class RenderTileDraconiumChest extends TileEntityRenderer<TileDraconiumChest> {

    public static final ResourceLocation DRACONIUM_CHEST = new ResourceLocation(DraconicEvolution.MODID, DETextures.DRACONIUM_CHEST);

    private final ModelDraconiumChest chestModel = new ModelDraconiumChest(RenderType::entitySolid);

    public RenderTileDraconiumChest(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileDraconiumChest tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer getter, int packedLight, int packedOverlay) {
        float r = (float) ((tile.colour.get() >> 16) & 0xFF) / 255f;
        float g = (float) ((tile.colour.get() >> 8) & 0xFF) / 255f;
        float b = (float) (tile.colour.get() & 0xFF) / 255f;

        BlockState state = Objects.requireNonNull(tile.getLevel()).getBlockState(tile.getBlockPos());
        if (state.getBlock() != DEContent.draconium_chest) return;

        Direction facing = state.getValue(DraconiumChest.FACING);
        int facingRotationAngle = 0;
        switch (facing.ordinal()) {
            case 2 : facingRotationAngle = 0;
                break;
            case 3 : facingRotationAngle = 180;
                break;
            case 4 : facingRotationAngle = 90;
                break;
            case 5 : facingRotationAngle = -90;
                break;
        }

        float lidAngle = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTicks;
        lidAngle = 1.0F - lidAngle;
        lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
        lidAngle = lidAngle * ((float) Math.PI /2f);

        chestModel.setLidAngle(lidAngle);
        chestModel.setFacingDirection(facingRotationAngle);
        chestModel.renderToBuffer(matrixStack, getter.getBuffer(chestModel.renderType(DRACONIUM_CHEST)), packedLight, packedOverlay, r, g, b, 1F);
    }
}
