/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.tywrapstudios.constructra.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;

/**
 * A block used for any Frame Shaped block, has a set {@link VoxelShape}, so make sure the model adapts to it.
 */
public class FrameShapedBlock extends Block {
    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(0, 0, 0, 2, 2, 16),
            Block.createCuboidShape(14, 0, 0, 16, 2, 16),
            Block.createCuboidShape(0, 14, 0, 2, 16, 16),
            Block.createCuboidShape(2, 14, 14, 14, 16, 16),
            Block.createCuboidShape(2, 14, 0, 14, 16, 2),
            Block.createCuboidShape(0, 2, 0, 2, 14, 2),
            Block.createCuboidShape(0, 2, 14, 2, 14, 16),
            Block.createCuboidShape(14, 2, 14, 16, 14, 16),
            Block.createCuboidShape(14, 2, 0, 16, 14, 2),
            Block.createCuboidShape(14, 14, 0, 16, 16, 16),
            Block.createCuboidShape(2, 0, 0, 14, 2, 2),
            Block.createCuboidShape(2, 0, 14, 14, 2, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public FrameShapedBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
