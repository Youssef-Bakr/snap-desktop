package org.esa.snap.product.library.ui.v2.repository.output;

import org.esa.snap.product.library.ui.v2.repository.AbstractRepositoryProductPanel;
import org.esa.snap.product.library.ui.v2.repository.local.LocalProgressStatus;
import org.esa.snap.product.library.ui.v2.repository.remote.DownloadProgressStatus;
import org.esa.snap.remote.products.repository.RepositoryProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by jcoravu on 30/1/2020.
 */
public class OutputProductResults {

    public static final ImageIcon EMPTY_ICON;
    static {
        BufferedImage emptyImage = new BufferedImage(75, 75, BufferedImage.TYPE_INT_ARGB);
        EMPTY_ICON = new ImageIcon(emptyImage);
    }

    private final Map<RepositoryProduct, ImageIcon> scaledQuickLookImages;
    private final Map<RepositoryProduct, LocalProgressStatus> localProductsMap;
    private final Map<RepositoryProduct, DownloadProgressStatus> downloadingProductsProgressValue;
    private final List<RepositoryProduct> availableProducts;

    private int currentPageNumber;

    public OutputProductResults() {
        this.scaledQuickLookImages = new HashMap<>();
        this.downloadingProductsProgressValue = new HashMap<>();
        this.localProductsMap = new HashMap<>();
        this.availableProducts = new ArrayList<>();

        this.currentPageNumber = 0;
    }

    public LocalProgressStatus getOpeningProductStatus(RepositoryProduct repositoryProduct) {
        return this.localProductsMap.get(repositoryProduct);
    }

    public Map<RepositoryProduct, LocalProgressStatus> getLocalProductsMap() {
        return localProductsMap;
    }

    public DownloadProgressStatus getDownloadingProductsProgressValue(RepositoryProduct repositoryProduct) {
        return this.downloadingProductsProgressValue.get(repositoryProduct);
    }

    public void addDownloadingProductsProgressValue(RepositoryProduct repositoryProduct, DownloadProgressStatus downloadProgressStatus) {
        this.downloadingProductsProgressValue.put(repositoryProduct, downloadProgressStatus);
    }

    public DownloadProgressStatus getDownloadingProductProgressValue(RepositoryProduct repositoryProduct) {
        return this.downloadingProductsProgressValue.get(repositoryProduct);
    }

    public ImageIcon getProductQuickLookImage(RepositoryProduct repositoryProduct) {
        ImageIcon imageIcon = this.scaledQuickLookImages.get(repositoryProduct);
        if (imageIcon == null) {
            if (repositoryProduct.getQuickLookImage() == null) {
                imageIcon = EMPTY_ICON;
            } else {
                Image scaledQuickLookImage = repositoryProduct.getQuickLookImage().getScaledInstance(EMPTY_ICON.getIconWidth(), EMPTY_ICON.getIconHeight(), BufferedImage.SCALE_FAST);
                imageIcon = new ImageIcon(scaledQuickLookImage);
                this.scaledQuickLookImages.put(repositoryProduct, imageIcon);
            }
        }
        return imageIcon;
    }

    public void removePendingDownloadProducts() {
        java.util.List<RepositoryProduct> keysToRemove = new ArrayList<>(this.downloadingProductsProgressValue.size());
        Iterator<Map.Entry<RepositoryProduct, DownloadProgressStatus>> it = this.downloadingProductsProgressValue.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<RepositoryProduct, DownloadProgressStatus> entry = it.next();
            DownloadProgressStatus progressPercent = entry.getValue();
            if (progressPercent.isPendingDownload()) {
                keysToRemove.add(entry.getKey());
            } else if (progressPercent.isDownloading()) {
                if (progressPercent.getValue() < 100) {
                    progressPercent.setStatus(DownloadProgressStatus.STOP_DOWNLOADING);
                }
            }
        }
        for (int i=0; i<keysToRemove.size(); i++) {
            this.downloadingProductsProgressValue.remove(keysToRemove.get(i));
        }
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getCurrentPageNumber() {
        return this.currentPageNumber;
    }

    public int getAvailableProductCount() {
        return this.availableProducts.size();
    }

    public void addProducts(List<RepositoryProduct> products) {
        this.availableProducts.addAll(products);
    }

    public RepositoryProduct getProductAt(int index) {
        return this.availableProducts.get(index);
    }
}
