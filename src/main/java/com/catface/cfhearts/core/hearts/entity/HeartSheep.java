package com.catface.cfhearts.core.hearts.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HeartSheep extends HeartArrow{

    public int radius = 1;

    public HeartSheep(World worldIn) {
        super(worldIn);
        this.speed = 1.0f;
        this.maxTime = 200;
    }

    @Override
    public void onActivation() {

    }

    @Override
    public void onAbilityUpdate() {
        super.onAbilityUpdate();
        if(this.world instanceof WorldServer){
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST,false,this.posX,this.posY,this.posZ,5,this.width/2,this.height/2,this.width/2,0.1, Block.getStateId(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN)));
        }
    }

    @Override
    public void onEnd() {
        float yaw = Math.round(this.rotationYaw/90)*90.0f;
        generateWall(this.world,this.getPosition(),EnumFacing.UP,1,yaw);
    }

    @Override
    public void onImpact(RayTraceResult result) {
        float yaw = Math.round(this.rotationYaw/90)*90.0f;
        if(result.typeOfHit == RayTraceResult.Type.BLOCK){
            generateWall(this.world,result.getBlockPos(),result.sideHit,1,yaw);
        }

        if(result.typeOfHit == RayTraceResult.Type.ENTITY){
            generateWall(this.world,new BlockPos(result.hitVec),EnumFacing.UP,1,yaw);
        }
        this.setDead();
    }

    public void generateWall(World world, BlockPos pos, EnumFacing side,int radius,double yaw){
        Vec3d corner1 = Vec3d.ZERO;
        Vec3d corner2 = Vec3d.ZERO;
        BlockPos mid = pos.offset(side,radius+1);
        switch(side){
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                corner1 = new Vec3d(mid.getX()+0.5-radius,mid.getY()+0.5,mid.getZ()+0.5-radius);
                corner2 = new Vec3d(mid.getX()+0.5+radius,mid.getY()+0.5,mid.getZ()+0.5+radius);
                break;
            case UP:
            case DOWN:
                double x = Math.cos(Math.toRadians(yaw))*radius;
                double z = Math.sin(Math.toRadians(yaw))*radius;
                corner1 = new Vec3d(mid.getX()+0.5-x,mid.getY()+0.5-radius,mid.getZ()+0.5-z);
                corner2 = new Vec3d(mid.getX()+0.5+x,mid.getY()+0.5+radius,mid.getZ()+0.5+z);
                break;
        }

        double minX = Math.min(corner1.x,corner2.x);
        double minY = Math.min(corner1.y,corner2.y);
        double minZ = Math.min(corner1.z,corner2.z);

        double maxX = Math.max(corner1.x,corner2.x);
        double maxY = Math.max(corner1.y,corner2.y);
        double maxZ = Math.max(corner1.z,corner2.z);

        for(double i = minX;i<=maxX;i++){
            for(double j = minY;j<=maxY;j++){
                for(double k = minZ;k<=maxZ;k++){
                    BlockPos pos2 = new BlockPos(i,j,k);
                    if(world.isAirBlock(pos2)){
                        world.setBlockState(pos2, Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN),3);
                    }
                }
            }
        }
    }
}
