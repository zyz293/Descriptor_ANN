clear;clc;
data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\characterization_leave_s_k.csv',1,1);
X = zscore(data);
X = X';
Y = pdist(X, 'euclidean');  
% default 'euclidean' seuclidean cityblock minkowski chebychev  cosine
% correlation spearman 

%squareform(Y)
Z = linkage(Y, 'average');  % default 'single' average centroid complete median ward weighted
%dendrogram(Z)
c = cophenet(Z,Y);
% linkage_default: default 0.7857 seuclidean 0.7563 cityblock 0.7918 chebychev 0.7967 minkowski 0.7521 
% linkage_average: spearman 0.7699 correlation & cosine 0.7894 chebychev
% 0.8449 minkowski 0.8872 euclidean 0.8872 cityblock 0.8609 seuclidean 0.8641
% linkage_centrod: euclidean 0.6668 seuclidean 0.7929  
% linkage_complete: spearman 0.6907 chebychev 0.7109 minkowski & default 0.7403
% linkage_median: seuclidean 0.7286 cityblock 0.7509 chebychev 0.8158
% linkage_ward: chebychev 0.7087
% linkage_weighted: default & minkowski 0.8854 
I = inconsistent(Z);
T = cluster(Z, 'maxclust', 6); % how many clusters you want to keep
%T = cluster(Z, 'cutoff', 1.145); % how many clusters you want to keep
%euclidean ward 5 minkowski ward 5 

characterization = fopen('characterization_leave_s_k.csv');
name = textscan(characterization, '%s', 31);  % total is 57, leave_sk is 31
name2 = name{1,1}{1,1};
newname = strsplit(name2, ',');
fid = fopen('hierarchical_cluster.csv', 'w');
for j = 1:max(T)  
    for i = 1:size(T,1)-1
        if T(i,1) == j
            fprintf(fid, '%s,', newname{1,i+1});
        end
    end
    if T(30,1) == j  % total is 56, leave_sk is 30
        fprintf(fid, '%s\n', newname{1,i+1});
    else
        fprintf(fid,'%s\n',' ');
    end
end
fclose(fid);
