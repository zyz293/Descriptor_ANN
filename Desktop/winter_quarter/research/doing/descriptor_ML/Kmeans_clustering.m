clear;clc;
data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\characterization_leave_s_k.csv',1,1);
X = zscore(data);
X = X';
[idx, C, sumd, D] = kmeans(X, 3, 'Display', 'final',...
    'Distance', 'correlation',...
    'EmptyAction', 'singleton',...
    'MaxIter', 1000000,...
    'Replicates', 1000); % sqeuclidean   cityblock   cosine   correlation   
%%%%%%%%% 5 6 seems ok; 3 is good 
figure;
[silh, h] = silhouette(X, idx);
h = gca;
h.Children.EdgeColor = [.8 .8 1];
xlabel 'Silhouette Value';
ylabel 'Cluster';
sum(sumd);
cluster = mean(silh)
% 3: correlation 0.2049
% 4: correlation 0.2293 cosine 0.2352 sqeuclidean 0.2359
% 5: sqeuclidean 0.2564
% 6: correlation 0.2819 cosine 0.2844 sq 0.3011
% 7: sq 0.3301
% 8: sq 0.3470
% sqeuclidean 5; cosine 4
characterization = fopen('characterization_leave_s_k.csv');   % change toal and leave_sk, you should change file here 
name = textscan(characterization, '%s', 31);   % total is 57, leave_sk is 31
name2 = name{1,1}{1,1};
newname = strsplit(name2, ',');
fid = fopen('Kmeans_cluster.csv', 'w');
for j = 1:max(idx)  
    for i = 1:size(idx,1) -1 
        if idx(i,1) == j
            fprintf(fid, '%s,', newname{1,i+1});
        end
    end
    if idx(30,1) == j    % total is 56, leave_sk is 30
        fprintf(fid, '%s\n', newname{1,31});   % total is 57, leave_sk is 31
    else
        fprintf(fid,'%s\n',' ');
    end
end
fclose(fid);