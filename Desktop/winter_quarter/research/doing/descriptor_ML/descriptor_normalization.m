clear;clc;
data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\characterization_leave_s_k.csv',1,1);
fid = fopen('chracterization_normalization_leave_s_k.csv', 'w');
%for i = 1:56
%    clear max;
%    clear min;
%    max = max(data(:,i));
%    min = min(data(:,i));
%    for j = 1:193
%        data(j,i) = (data(j,i) - min)/(max - min);
%    end
%end
data = zscore(data);
for j = 1:193
    for i = 1:29    % total is 55, leave_sk is 29
        fprintf(fid, '%d,', data(j,i));
    end
    fprintf(fid, '%d\n', data(j, 30));   % total is 56, leave_sk is 30
end
fclose(fid);