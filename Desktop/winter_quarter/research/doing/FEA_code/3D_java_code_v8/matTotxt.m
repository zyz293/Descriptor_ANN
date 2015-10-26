clear;clc;
load('./crop_terthiophenePGMA_2wt%_3D_structure_output.mat');
fid = fopen('./crop_terthiophenePGMA_2wt%_3D_structure_output.txt', 'w');
[row, col] = size(img_para);
fprintf(fid, '%d\n', row);
fprintf(fid, '%d\n', col);
for i = 1:row
    for j = 1:col-1
        fprintf(fid, '%d,', img_para(i,j));
    end
    fprintf(fid, '%d\n', img_para(i, col));
end
fclose(fid);