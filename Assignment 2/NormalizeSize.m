function [X]=NormalizeSize(X)
%Scales the data so that the height of the bounding box drawn
%around the data points is 2000 and its aspect ratio remains
%unchanged. Datapoints are the rows of X and they consists of x-
%and y-coordinates. (Nx2 matrices)



H=2000;
min1=min(X(:,1));
min2=min(X(:,2));
max1=max(X(:,1));
max2=max(X(:,2));
h=max2-min2;
scale=H/h;
BBC=[min1+max1, min2+max2]/2; %Center of the bounding box

N=size(X,1);
for i=1:1:N
  X(i,:)=scale*(X(i,:)-BBC)+BBC;
end